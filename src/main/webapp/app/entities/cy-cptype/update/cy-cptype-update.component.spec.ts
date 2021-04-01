jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { CyCptypeService } from '../service/cy-cptype.service';
import { ICyCptype, CyCptype } from '../cy-cptype.model';

import { CyCptypeUpdateComponent } from './cy-cptype-update.component';

describe('Component Tests', () => {
  describe('CyCptype Management Update Component', () => {
    let comp: CyCptypeUpdateComponent;
    let fixture: ComponentFixture<CyCptypeUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let cyCptypeService: CyCptypeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [CyCptypeUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(CyCptypeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CyCptypeUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      cyCptypeService = TestBed.inject(CyCptypeService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should update editForm', () => {
        const cyCptype: ICyCptype = { id: 456 };

        activatedRoute.data = of({ cyCptype });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(cyCptype));
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const cyCptype = { id: 123 };
        spyOn(cyCptypeService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ cyCptype });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: cyCptype }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(cyCptypeService.update).toHaveBeenCalledWith(cyCptype);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const cyCptype = new CyCptype();
        spyOn(cyCptypeService, 'create').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ cyCptype });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: cyCptype }));
        saveSubject.complete();

        // THEN
        expect(cyCptypeService.create).toHaveBeenCalledWith(cyCptype);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject();
        const cyCptype = { id: 123 };
        spyOn(cyCptypeService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ cyCptype });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(cyCptypeService.update).toHaveBeenCalledWith(cyCptype);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });
  });
});
